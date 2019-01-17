(ns marsexplorer.logic)

(defn- position-x-1 [position] (update position :x dec))

(defn- position-x+1 [position] (update position :x inc))

(defn- position-y-1 [position] (update position :y dec))

(defn- position-y+1 [position] (update position :y inc))

(defn- move
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

(defn- turn
  "Returns a cardinal direction. N, E, S, W
  Explorers can turn-left or turn-right and these turns will
  result in a new cardinal direction.
  This function will always look to the left side, to make this possible
  when the explorer request to turn right, a list of possible
  cardinal directions will be reverted.
  Comparisons are always made with the next item. If a match is found,
  it means that the current item is what we are looking for."
  [side-to-turn directions current-direction]
  (if (= side-to-turn :R)
    (turn :L (reverse directions) current-direction)
    ;;prepend last item of directions '(:W :N :E :S :W)
    ;;if north is the current direction, to turn left will result in west
    (->> (conj directions (last directions))
         (partition 2 1)
         (keep (fn [[current next]]
                  (if (= next current-direction) current)))
         (first))))

(defn execute-instructions [position instructions directions]
  (if (empty? instructions)
    position
    (case (first instructions)
      :M (execute-instructions (move position) (rest instructions) directions)
      (:L :R) (execute-instructions (assoc position :direction
                                                  (turn (first instructions)
                                                        directions
                                                        (:direction position)))
                               (rest instructions)
                               directions))))

()