(ns notebooks.financial-intro
  (:require [nextjournal.clerk :as clerk]
            [contracts.primitives :as p]
            [contracts.valuation :as v]))

;; # Rowing Machine Driven Development: Financial Contracts
;; *Guided by the ghost of Haskell, implemented in the data of Clojure.*

;; ## 1. Defining the Primitives
;; We define a simple Zero Coupon Bond logic.
;; Represents receiving 1 unit of USD.
(def c1 (p/one-contract :USD))

;; ## 2. Composing Contracts
;; Let's create a basic currency swap. 
;; I give you GBP, you give me USD.
(def swap-contract
  (p/and-contract
    (p/one-contract :USD)
    (p/give (p/scale 0.8 (p/one-contract :GBP)))))

;; Let's visualize the data structure (AST) representing this trade:
swap-contract

;; ## 3. Valuation
;; We define the market state as a simple map of exchange rates.
(def market-state {:USD 1.0 :GBP 1.25})

;; We calculate the value of the swap based on the market state.
;; Calculation: (1.0 * USD) - (0.8 * (1.25 * USD))
;; Expected: 1.0 - 1.0 = 0
(v/valuate swap-contract market-state)

;; ## 4. Scenario Analysis
;; What if the GBP strengthens to 1.50?
(def market-bull-gbp {:USD 1.0 :GBP 1.50})

(v/valuate swap-contract market-bull-gbp)
;; Result should be negative (we are giving away more value than receiving).