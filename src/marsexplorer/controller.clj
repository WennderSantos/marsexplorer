(ns marsexplorer.controller
  (:require [marsexplorer.logic :as logic]
            [marsexplorer.specs :as specs]))

(defn- execute [instruction position cardinal-directions]
  (condp = instruction
    :M (logic/move position)
    (logic/turn instruction cardinal-directions position)))

(defn- handle-instructions [{:keys [position instructions]}]
  (cond
    (empty? instructions)
      (logic/get-result position)
    (logic/validPosition? position {:bottom-left {:x 0 :y 0}
                                                  :top-right {:x 6 :y 6}})
      (handle-instructions {:position (execute (first instructions)
                                               position
                                               specs/cardinal-directions)
                            :instructions (rest instructions)})
    :else (str "Invalid position " (logic/get-result position))))

(defn handle-explorers [explorers]
  (map #(handle-instructions %) explorers))
