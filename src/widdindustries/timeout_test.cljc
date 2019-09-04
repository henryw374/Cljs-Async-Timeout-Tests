(ns widdindustries.timeout-test
  (:require [clojure.test :refer [is]])
  #?(:cljs (:require-macros [widdindustries.timeout-test :refer [async-timeout async-timeout-at]])))

(defmacro async-timeout-at 
  "wait-for a user specified amount of time before failing"
  [wait-for done & body]
  (let [new-done (gensym)
        timeout (gensym)
        ex (gensym)
        ex-fn (gensym)
        result (gensym)]
    `(cljs.test/async ~new-done
       (let [~timeout (js/setTimeout (fn []
                                       (is (= 1 0) "Test timed out")
                                       (~new-done))
                        ~wait-for)
             ~done (fn []
                     (js/clearTimeout ~timeout)
                     (~new-done))
             ~ex-fn (fn [~ex]
                      (is (not ~ex))
                      (~done))]
         (try
           (let [~result (do ~@body)]
             (when (.-catch ~result)
               (.catch ~result ~ex-fn)))
           (catch js/Error ~ex (~ex-fn ~ex)))))))

(defmacro async-timeout
  "wait-for 1 second before failing"
  [done & body]
  `(async-timeout-at 1000 ~done ~@body))


