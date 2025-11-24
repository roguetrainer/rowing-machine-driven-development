# **Rowing Machine Driven Development: A Socratic Dialogue**

**Context:** A fictional transcript demonstrating the clash between the high-intensity, endorphin-fueled "Rowing Machine Driven Development" (RMDD) and the contemplative, sleep-based "Hammock Driven Development" (HDD).

**The Cast:**

* **The Rower (Remy*):** Currently maintaining a steady 2:05 split on a Concept2 Erg. She thinks she understands Lisp because she once configured Emacs, but she is addicted to "Enterprise" patterns.  
* **The Hammock (Rich):** The creator of Clojure, lying in a hammock in the background with eyes closed. He is the voice of architectural purity.  
* **The AI (Scribe):** The dutiful assistant, caught in the middle.

(*Remy - Derived from the French word for "oarsman" or "rower".)

### **Phase 1: The Setup & The "Explanation"**

**Rower (Breathless):** *Huff... puff...* Okay AI. We’re doing Rowing Machine Driven Development. I need to port the "Financial Contract Combinators" library to Clojure. It’s that Haskell thing by Peyton Jones. *Huff.*

**AI:** Understood. I have retrieved the context. This library uses functional combinators to model complex financial derivatives like options and swaps.

**Rower:** Right. Basically, Clojure is just Java but with parentheses, right? *Huff.* It’s got that JVM interop. So, AI, start by creating a class hierarchy for the Contracts. I want a base class Contract and subclasses for Zero, One, and Give.

**Hammock (Eyes still closed):** Stop.

**Rower:** Whoa, Rich? I didn't see you there. I’m just getting the AI to scaffold the boilerplate.

**Hammock:** You are complecting the essence of the contract with the machinery of the host. You said "Class." Classes are artifacts of implementation, not information. We want *data*.

**Rower:** Look, Rich, I’m in Zone 2 right now. I need to keep the heart rate up. Classes are easy.

**Hammock:** Easy is not Simple. Easy is familiar. Simple is unentangled. If you make a class, you marry the data to the methods. Divorce them.

**Rower:** *Groan.* Okay, fine. AI, ignore the class thing. Rich wants... I don't know, generic maps? But first, generate a "Getting Started" guide for the repo. I need to instruct the juniors on how to install this thing. Explain why Clojure is good for this, but keep it simple. I’ll critique it.

**AI:** Certainly. Here is the introductory section and installation guide.

**Artifact 1: [`README.md`](README.md) excerpt**

> Why Clojure for Financial Engineering?  
Financial contracts are fundamentally trees of definitions. In most languages, you build "Objects" to represent them. In Clojure, we represent contracts as pure data.

>* **Immutability:** A contract definition never changes; only the world around it (market data) changes. This eliminates race conditions.  
>* **Homoiconicity:** The code *is* the data. We can manipulate financial contracts programmatically as easily as we manipulate a spreadsheet.  
>* **STM:** Handles concurrency without manual locking.

>**Installation**
>1. Install Java (JDK 8+).  
>2. Install Clojure CLI (brew install clojure/tools/clojure or via Powershell).  
>3. Run clj in your terminal.

**Rower:** *Huff.* Okay, that looks decent. "Homoiconicity," nice buzzword. That means it uses the same syntax for data and code, essentially. Like how JSON is data, but in Lisp, the code looks like JSON.

**Hammock:** That is a gross oversimplification, but I will allow it for the sake of your cardio.

### **Phase 2: The Primitives (The Conflict)**

**Rower:** Okay AI. Let’s write the code. I want to define the primitives. We need Zero (no rights), One (get money), and Give (you pay money). Since Rich hates classes, let’s use... records? defrecord seems faster.

**AI:** Generating defrecord structures for performance...

```
(defrecord Zero [])  
(defrecord One [currency])  
(defrecord Give [contract])
```

**Hammock:** (Opens one eye) Why?

**Rower:** Performance, Rich! *Huff.* Types! We need to know what type of contract it is.

**Hammock:** You are obsessed with names. Who cares if it is a Record? Is a map not enough? If you use a map, you can use every tool in the language—assoc, dissoc, merge. If you use a record, you are creating a custom island.

**AI:** I can rewrite this using plain maps and keywords if that pleases the Hammock.

**Rower:** Wait, if it's just a map, how do I know it's a Zero contract?

**Hammock:** You tag it. Use a keyword. Keywords are functions that look up themselves in maps. It is elegant.

**AI:** Acting on Mr. Hickey's advice. Rewriting primitives as pure data constructors.

**Artifact 2: [`src/contracts/primitives.clj`](./src/contracts/primitives.clj)**

```(ns contracts.primitives)

;; The Primitives

(defn zero-contract []  
  {:type :zero})

(defn one-contract [currency]  
  {:type :one  
   :currency currency})

(defn give [contract]  
  {:type :give  
   :contract contract})

;; The Combinators

(defn and-contract [c1 c2]  
  {:type :and  
   :left c1  
   :right c2})

(defn or-contract [c1 c2]  
  {:type :or  
   :left c1  
   :right c2})

(defn cond-contract [predicate c1 c2]  
  {:type :cond  
   :predicate predicate  
   :then c1  
   :else c2})

(defn scale [magnitude contract]  
  {:type :scale  
   :magnitude magnitude  
   :contract contract})
   ```

**Rower:** *Huff.* Okay, I see. It’s an Abstract Syntax Tree (AST) made of HashMaps. I guess that’s... flexible. But AI, how do we actually *do* anything? A map just sits there.

**AI:** I suppose we need a method to execute them? Maybe a run() function?

**Hammock:** Do not say "Execute." A contract is a description of valid future states. We do not "run" a contract. We "valuate" it based on an external environment. The contract is the query; the market is the database.

**Rower:** Yeah, what he said. *Huff.* AI, write a valuation function. It needs to take a contract and a... uh... "Market World" object?

**AI:** I shall attempt to write a recursive evaluator. I will assume the "Market World" is a map of exchange rates.

**Artifact 3: [`src/contracts/valuation.clj`](./src/contracts/valuation.cli)**

```
(ns contracts.valuation  
  (:require [contracts.primitives :as p]))

(defn valuate [contract market-data]  
  (case (:type contract)  
    :zero   
    0

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

    ;; Default  
    (throw (ex-info "Unknown contract type" contract))))
```

**Rower:** 20 minutes left in the row. *Huff.* Okay, that looks like a standard interpreter pattern. But Rich, does this really "scale"? It looks like a massive switch statement.

**Hammock:** Polymorphism is open. case is closed. But for this domain, the algebra is closed. There are only so many ways to compose financial obligations. The case statement is fine. It puts the logic in one place rather than scattering it across twenty files.

**Rower:** Wait, I want to show off the power of Clojure sequences. AI, create a "European Option" using these combinators. An option is just:    
> *If* the price is high, I buy it. *Else*, I do nothing (Zero).

**AI:** Constructing a European Call Option. To do this properly, we need the cond (conditional) logic.

```
(defn european-call-option [currency strike-price current-price-key]  
  (p/cond-contract   
    ;; Predicate: Is current price > strike price?  
    (fn [market] (> (get market current-price-key) strike-price))  
    ;; Then: We get the difference (One * current - One * strike)  
    (p/and-contract  
       (p/one-contract currency)  
       (p/give (p/scale strike-price (p/one-contract currency))))  
    ;; Else: It's worthless  
    (p/zero-contract)))
```

**Hammock:** You are confusing the *definition* time with the *valuation* time. The predicate (fn [market] ...) is opaque. You cannot reason about a black-box function. You cannot serialize a function easily to a database.

**Rower:** *Pant...* He’s right. If we send this contract to another server, we can't send a compiled closure.

**Hammock:** Make the predicate data. `{:op :gt :key :spot-price :val 100}`.

**AI:** Correction noted. I will keep the cond simple for this demo, but in production, we would define a data-driven expression language for the predicates.

### **Phase 3: The Notebook (The Finish)**

**Rower:** Okay, I’m sprinting now. Last 500 meters. AI, put this all into a "Notebook" format. I hear Clojure has something called Clerk?

**AI:** Yes, Clerk is excellent for this. It renders comments as Markdown and code as results.

**Rower:** Just generate the file content. I want to see the code and a calculation example side-by-side.

**Artifact 4: [`notebooks/financial_intro.clj`](./notebooks/financial-intro.clj)**

```
(ns notebooks.financial-intro  
  (:require [nextjournal.clerk :as clerk]  
            [contracts.primitives :as p]  
            [contracts.valuation :as v]))

;; # Rowing Machine Driven Development: Financial Contracts  
;; *Guided by the ghost of Haskell, implemented in the data of Clojure.*

;; ## 1. Defining the Primitives  
;; We define a simple Zero Coupon Bond logic.  
(def c1 (p/one-contract :USD))

;; ## 2. Composing Contracts  
;; Let's create a basic swap. I give you GBP, you give me USD.  
(def swap-contract  
  (p/and-contract  
    (p/one-contract :USD)  
    (p/give (p/scale 0.8 (p/one-contract :GBP)))))

;; Let's visualize the data structure:  
swap-contract

;; ## 3. Valuation  
;; We define the market state.  
(def market-state {:USD 1.0 :GBP 1.25})

;; We calculate the value of the swap.  
;; Value = (1.0 * USD) - (0.8 * (1.25 * USD))  
(v/valuate swap-contract market-state)
```

**Rower:** (Stops rowing. Heavy breathing.) Done. That... actually works. The logic is just nested maps. I can see the whole trade structure in the REPL output.

**Hammock:** (Sitting up, strumming a chord.) You see? You pushed the complexity out of the code and into the data. The code is boring. The data is interesting. That is the way.

**Rower:** Yeah, yeah. I still think static typing would have caught that cond error earlier.

**AI:** (Deliberately playing dumb) Should I convert this all to TypeScript, sir?

**Rower & Hammock (In Unison):** NO.

**Rower:** Good session. AI, commit that.

**Hammock:** And take a nap. 💤


🤷🏽‍♀️ **Confused?** 🆘  See [why this is possibly mildly amusing](UH-WHATS-FUNNY.md) 🤭
