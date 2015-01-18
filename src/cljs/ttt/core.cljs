(ns ttt.core)

(defn init-board []
  [[nil nil nil]
   [nil nil nil]
   [nil nil nil]])

(defn init-game []
  {:board (init-board)
   :next-player :x})

(defn- toggle-player [p]
  (if (= :x p)
    :o
    :x))

(defn mark [board player [row col]]
  (when-not (and (<= 0 row 2)
                 (<= 0 col 2))
    (throw (js/Error. "Cannot place mark out of bounds")))
  (when (get-in board [row col] player)
    (throw (js/Error. "Location already marked")))
  (assoc-in board [row col] player))

(defn move [{:keys [board next-player] :as game} player location]
  (when-not (= next-player player)
    (throw (js/Error. "Wrong player")))
  (assoc game
    :board (mark board player location)
    :next-player (toggle-player next-player)))
