# **Financial Contract Combinators (RMDD Edition)**

*A Clojure implementation of Composable Financial Contracts, built at 145 BPM.*

---
![RMDD](./img/RMDD.png)
---

🪂 In a hurry? Jump straight to the *Rowing Machine Driven Development* docs:
* 📜 [RMDD-MANIFESTO.md](./docs/RMDD-MANIFESTO.md) 
* 👥 [RMDD-SOCRATIC-DIALOGUE.md](./docs/RMDD-SOCRATIC-DIALOGUE.md) 

## **Overview**

This library provides a domain-specific language (DSL) for defining financial contracts as **pure data**. Inspired by the papers of Peyton Jones and Eber, but implemented using the philosophy of **Rowing Machine Driven Development**. 🚣🏼‍♀️

This library provides a domain-specific language (DSL) for defining financial contracts as **pure data**. Inspired by the papers of Peyton Jones and Eber and [the original Haskell implementation](https://github.com/roguetrainer/financial-contract-combinators), but translated to Clojure using the philosophy of **Rowing Machine Driven Development**.

Instead of opaque objects or classes, we use immutable maps to represent the Abstract Syntax Tree (AST) of a financial instrument. This allows for:

* **Composition:** Combine simple contracts (Zero, One) into complex derivatives (Swaps, Options).  
* **Analysis:** Contracts can be analyzed, transformed, and optimized before valuation.  
* **Safety:** No race conditions, thanks to Clojure's immutable data structures.

## **Context: The Hammock & The Rower**

**Clojure** is a functional Lisp for the JVM created by **Rich Hickey**. Hickey is famously known for his arguably radical philosophy on software design, specifically **"Hammock Driven Development" (HDD)**. He argued that "typing is not thinking" and that the hardest problems are solved away from the keyboard, often while resting to allow the subconscious to process complexity.

* [Watch the Talk: Hammock Driven Development](https://www.youtube.com/watch?v=f84n5oFoZBc)  
* [Read the Transcript](https://www.google.com/search?q=https://github.com/matthiasn/talk-transcripts/blob/master/Hickey_Rich/HammockDrivenDev.md)

### **The Philosophy: RMDD**

We take Hickey's concept one step further. If typing is not thinking, neither is sitting. This library was dictated between strokes on a rowing machine.

---   
![RMDD](./img/No-chairs.png)  
---   

* **The Manifesto:** Read [RMDD-MANIFESTO.md](https://www.google.com/search?q=RMDD-MANIFESTO.md) to understand why we believe "Sitting is not thinking."  
* **The Origin Story:** Read [RMDD-SOCRATIC-DIALOGUE.md](https://www.google.com/search?q=RMDD-SOCRATIC-DIALOGUE.md) for the transcript of the session where Rich Hickey (from a hammock) and a rower architected this system.

## **Why Clojure?**

Financial contracts are fundamentally trees of definitions.

* **Immutability:** A contract definition never changes; only the world around it (market data) changes.  
* **Homoiconicity:** The code *is* the data. We can manipulate financial contracts programmatically as easily as we manipulate a spreadsheet.  
* **STM (Software Transactional Memory):** Handles concurrency without manual locking, crucial for high-frequency trading simulations.

## **Installation**

1. **Install Java:** Ensure you have a JDK (version 8+).  
2. **Install the Clojure CLI:**  
   * **Mac:** `brew install clojure/tools/clojure`  
   * **Linux:** `curl -O https://download.clojure.org/install/linux-install...` (Check official docs)  
   * **Windows:** `iwr -useb download.clojure.org/install/win-install.ps1 | iex`  
3. **Run the REPL:** Type `clj` in your terminal.

## **Usage**

See [`notebooks/financial_intro.clj`](./notebooks/financial-intro.clj) for a walkthrough of the primitives and valuation engine.


🤷🏽‍♀️ **Confused?** 🆘  See [why this is possibly mildly amusing](./docs/UH-WHATS-FUNNY.md) 🤭
