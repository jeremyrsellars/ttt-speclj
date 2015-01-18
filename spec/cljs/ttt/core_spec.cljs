(ns ttt.core-spec 
  (:require-macros [speclj.core :refer
    [context describe it
     with with-all
     should= should should-throw]])
  (:require [speclj.core]
            [ttt.core :as ttt]))

(def next-player :next-player)

(def ^:constant cannot-mark-out-of-bounds "Cannot place mark out of bounds")
(def ^:constant location-alread-marked "Location already marked")
(def ^:constant wrong-player "Wrong player")

(context "A tic-tac-toe game"
  (with-all game (ttt/init-game))
  (with-all board (:board @game))
  (it "has three rows"
    (should= 3 (count @board)))
  (it "has three columns"
    (should (every? #(== 3 (count %)) @board)))
  (it "shows x as next player"
    (should= :x (get @game next-player )))

  (describe "when empty"
    (it "has no marks"
      (should (every? #(every? nil? %) @board)))
    (it "cannot mark above board"
      (should-throw js/Error cannot-mark-out-of-bounds
        (ttt/mark @board :x [-1 0])))
    (it "cannot mark below board"
      (should-throw js/Error cannot-mark-out-of-bounds
        (ttt/mark @board :x [3 0])))
    (it "cannot mark to left of board"
      (should-throw js/Error cannot-mark-out-of-bounds
        (ttt/mark @board :x [0 -1])))
    (it "cannot mark below board"
      (should-throw js/Error cannot-mark-out-of-bounds
        (ttt/mark @board :x [0 3]))))

  (describe "when an x is placed in first row, second column"
    (it "has an x only in [0 1]"
      (should=
        [[nil :x nil]
         [nil nil nil]
         [nil nil nil]]
        (-> @game
            (ttt/move :x [0 1]))
            :board))

    (it "only the next player can play"
      (should-throw js/Error wrong-player
        (ttt/move (-> (ttt/init-game) (ttt/move :x [1 1])) :x [0 1])))

    (it "cannot mark the same space again"
      (should-throw js/Error location-alread-marked
        (ttt/move (-> (ttt/init-game) (ttt/move :x [1 1])) :o [1 1])))))
