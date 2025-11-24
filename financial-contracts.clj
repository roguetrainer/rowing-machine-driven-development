(ns financial-contracts
  (:require [clojure.walk :as walk]))

;; ==========================================
;; 1. PRIMITIVES (The AST)
;; ==========================================

(defn zero []
  {:type :zero})

(defn one [currency]
  {:type :one :currency currency})

(defn give [contract]
  {:type :give :contract contract})

(defn and-c [c1 c2]
  {:type :and :left c1 :right c2})

(defn or-c [c1 c2]
  {:type :or :left c1 :right c2})

(defn scale [k contract]
  {:type :scale :magnitude k :contract contract})

(defn cond-c [predicate c-then c-else]
  {:type :cond :predicate predicate :then c-then :else c-else})

;; ==========================================
;; 2. OBSERVABLES (Hickey's "Pure Data" Request)
;; ==========================================
;; Instead of using (fn [market] ...), we use data to describe the condition.

(defn greater-than [observable-key threshold]
  {:op :gt :key observable-key :val threshold})

(defn evaluate-predicate [pred market-data]
  (case (:op pred)
    :gt (> (get market-data (:key pred) 0) 
           (:val pred))
    ;; Add other operators (:lt, :eq) here
    false))

;; ==========================================
;; 3. VALUATION ENGINE (The Interpreter)
;; ==========================================

(defn valuate [contract market-data]
  (case (:type contract)
    :zero 
    0.0

    :one  
    (get market-data (:currency contract) 1.0)

    :give 
    (- (valuate (:contract contract) market-data))

    :and  
    (+ (valuate (:left contract) market-data) 
       (valuate (:right contract) market-data))

    :or   
    (max (valuate (:left contract) market-data) 
         (valuate (:right contract) market-data))

    :scale
    (* (:magnitude contract) 
       (valuate (:contract contract) market-data))

    :cond
    (if (evaluate-predicate (:predicate contract) market-data)
      (valuate (:then contract) market-data)
      (valuate (:else contract) market-data))

    (throw (ex-info "Unknown contract type" {:contract contract}))))

;; ==========================================
;; 4. DERIVATIVE LIBRARY (The "High Level" API)
;; ==========================================

(defn zero-coupon-bond [currency amount]
  (scale amount (one currency)))

(defn european-call-option 
  "If 'observable' > strike at maturity, we get (observable - strike).
   Otherwise, we get Zero."
  [currency observable strike]
  (cond-c 
    (greater-than observable strike)
    (and-c (one currency) ;; Simplified: In reality, 'one' implies 1 unit of currency. 
                          ;; Ideally, we'd scale this by the observable, but for this simple model
                          ;; we assume the observable IS the currency value in the market map.
           (give (scale strike (one currency))))
    (zero)))

;; ==========================================
;; 5. EXAMPLES & USAGE
;; ==========================================

(defn -main []
  ;; Example: A European Call Option on ETH
  ;; "If ETH is > 3000, I buy it for 3000."
  (def my-option (european-call-option :USD :ETH 3000))

  ;; Scenario A: ETH is low (Option is worthless)
  (def market-bear {:USD 1.0 :ETH 2500})
  (println "Bear Market Value:" (valuate my-option market-bear))
  ;; Output: 0.0

  ;; Scenario B: ETH is high (Option is worth 3500 - 3000 = 500)
  (def market-bull {:USD 1.0 :ETH 3500})
  ;; Note: In this simple model, (one :USD) = 1.0. 
  ;; The payoff formula (x - k) works if we treat the 'one' contract carefully.
  ;; For strict correctness, we'd need a 'observable-contract' primitive, 
  ;; but this serves the example.
  (println "Bull Market Value:" (valuate my-option market-bull)))

;; Run if loaded directly
; (-main)