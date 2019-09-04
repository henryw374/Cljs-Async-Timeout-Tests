# Cljs Async Timeout Tests 

[![Clojars Project](https://img.shields.io/clojars/v/cljs-timeout-test.svg)](https://clojars.org/cljs-timeout-test)

The Clojurescript site describes [how to do async tests](https://clojurescript.org/tools/testing) using the
`cljs.test/async` macro. 

That works fine, but you have to make sure your test code calls the `done` function in every case, including 
on error, timeout etc. So this lib provides a macro like `cljs.test/async`, but which on timeout or uncaught failure (exception or failed promise), will fail the test, 
calling the `done` function for you.

## Usage 

```
(ns myns.ns
  (:require [clojure.test :refer [is deftest]]
            [widdindustries.timeout-test :refer [async-timeout async-timeout-at]]))

(deftest my-test
  (async-timeout done 
     ;; do some stuff that will call `done` when it succeeds.
     ;; lib expects any async body will result in a promise
   ))
```  

