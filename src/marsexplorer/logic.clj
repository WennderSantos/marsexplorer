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
  "Returns a cardinal direction. N, E, S, W
  Explorers can turn-left or turn-right and these turns will
  result in a new cardinal direction.
  This function will always look to the left side, to make this possible
  when the explorer request to turn right, a list of possible
  cardinal directions will be reverted.
  Comparisons are always made with the next item. If a match is found,
  it means that the current item is what we are looking for."
  [where cardinal-directions position]
  (if (= where :R)
    (turn :L (reverse cardinal-directions) position)
    ;;prepend last item of cardinal-directions '(:W :N :E :S :W)
    ;;if north is the current direction, turn to the left will result in west
    (->> (conj cardinal-directions (last cardinal-directions))
         (partition 2 1)
         (keep (fn [[current next]]
                  (if (= next (:direction position)) current)))
         (first)
         (assoc position :direction))))

(defn validPosition? [position {:keys [bottom-left top-right]}]
  (and
    (and (>= (:x position) (:x bottom-left))
         (>= (:y position) (:y bottom-left)))
    (and (<= (:x position) (:x top-right))
         (<= (:y position) (:y top-right)))))

(defn get-result [{:keys [x y direction]}]
  (str x " " y " " (name direction)))