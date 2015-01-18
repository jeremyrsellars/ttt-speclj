(ns ttt.core-spec 
  (:require-macros [speclj.core :refer [describe it should= run-specs]])
  (:require [speclj.core]
            [ttt.core :as tc]))

(describe "A ClojureScript test"
  (it "fails. Fix it!"
    (should= 0 1)))

