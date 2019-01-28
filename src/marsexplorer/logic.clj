(ns marsexplorer.logic)

(defn- position-x-1 [position] (update position :x dec))

(defn- position-x+1 [position] (update position :x inc))

(defn- position-y-1 [position] (update position :y dec))

(defn- position-y+1 [position] (update position :y inc))

(defn move
  "Returns a position.
  Move an explorer based on its position x y and direction.
  The explorer current direction will define which function f
  will be applied to its x y."
  [position]
  (-> {:W #(position-x-1 position)
       :E #(position-x+1 position)
       :S #(position-y-1 position)
       :N #(position-y+1 position)}
      ((fn [movements-by-direction]
        ((get-in movements-by-direction [(:direction position)]))))))

(defn turn
  "Returns a position.
  Explorers can be instructed to turn left or to turn right using
  the where parameter with a key :L to turn left or :R to turn right."
  [where position cardinal-directions]
  (if (= where :R)
    (turn :L position (reverse cardinal-directions))
    ;;prepend last item of cardinal-directions '(:W :N :E :S :W)
    ;;if north is the current direction, turn left will result in west
    (->> (conj cardinal-directions (last cardinal-directions))
         (partition 2 1)
         (keep (fn [[current next]]
                  (if (= next (:direction position)) current)))
         (first)
         (assoc position :direction))))

(defn validPosition? [position {:keys [bottom-left top-right]}]
 "Returns true or false."
 "Check if a position is valid based on mars length"
  (and
    (and (>= (:x position) (:x bottom-left))
         (>= (:y position) (:y bottom-left)))
    (and (<= (:x position) (:x top-right))
         (<= (:y position) (:y top-right)))))
