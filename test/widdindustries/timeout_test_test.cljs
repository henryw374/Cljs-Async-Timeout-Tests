(ns widdindustries.timeout-test-test
  (:require [clojure.test :refer [is deftest]]
            [widdindustries.timeout-test :refer [async-timeout async-timeout-at]]))

;;; expected to fail! :)
(deftest test-that-times-out-default
  (async-timeout done 
    (js/setTimeout (constantly "hello") 2000)))

(deftest test-that-times-out-specified
  (async-timeout-at 1500 done
    (js/setTimeout (constantly "hello") 2000)))

(deftest test-that-bombs-with-promise
  (async-timeout done
    (js/Promise. (fn [_ reject]
                   (js/setTimeout #(reject "Blow up!") 500)))))

(deftest test-that-bombs-synchronously
  (async-timeout done
    (throw (js/Error. "uh oh!"))))

(deftest test-that-passes
  (async-timeout done
    (is "hooray!")
    (js/setTimeout done 500)))

