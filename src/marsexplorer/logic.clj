(ns marsexplorer.logic
  (:require [marsexplorer.config :refer [cardinal-directions-turns]]))

(defn move
  "Returns a position.
  Move an explorer based on its position x y and direction.
  The explorer current direction will define which function f
  will be applied to its x y."
  [position]
  (-> {:W (update position :x dec)
       :E (update position :x inc)
       :S (update position :y dec)
       :N (update position :y inc)}
      ((fn [movements-by-direction]
        (get movements-by-direction (:direction position))))))

(defn turn
  "Returns a position.
  Explorers can be instructed to turn left or to turn right using
  the where parameter with a key :L to turn left or :R to turn right."
  [where position]
  (-> (get cardinal-directions-turns where)
      (get (:direction position))
      ((fn [new-direction]
        (assoc position :direction new-direction)))))

(defn validPosition? [position {:keys [bottom-left top-right]}]
 "Returns true or false."
 "Check if a position is valid based on mars length"
  (and
    (and (>= (:x position) (:x bottom-left))
         (>= (:y position) (:y bottom-left)))
    (and (<= (:x position) (:x top-right))
         (<= (:y position) (:y top-right)))))
