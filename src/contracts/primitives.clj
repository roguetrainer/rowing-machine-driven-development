(ns contracts.primitives)

;; =============================================================================
;; THE PRIMITIVES
;; Pure data constructors for the contract AST.
;; =============================================================================

(defn zero-contract []
  {:type :zero})

(defn one-contract [currency]
  {:type :one
   :currency currency})

(defn give [contract]
  {:type :give
   :contract contract})

;; =============================================================================
;; THE COMBINATORS
;; Functions that glue contracts together.
;; =============================================================================

(defn and-contract [c1 c2]
  {:type :and
   :left c1
   :right c2})

(defn or-contract [c1 c2]
  {:type :or
   :left c1
   :right c2})

(defn cond-contract 
  "Conditional contract. 
   predicate: A data structure describing the condition (e.g., {:op :gt ...})
   c1: The contract if true
   c2: The contract if false"
  [predicate c1 c2]
  {:type :cond
   :predicate predicate
   :then c1
   :else c2})

(defn scale [magnitude contract]
  {:type :scale
   :magnitude magnitude
   :contract contract})