(ns marsexplorer.controller
  (:require [marsexplorer.logic :as logic]
            [marsexplorer.specs :as specs]
            [marsexplorer.adapters :as adapters]))

(defn- execute [instruction position cardinal-directions]
  (case instruction
    :M (logic/move position)
    (logic/turn instruction cardinal-directions position)))

(defn- handle-instructions [{:keys [position instructions]}]
  (cond
    (empty? instructions)
      (adapters/position->cmdline-fmt position)
    (logic/validPosition? position {:bottom-left {:x 0 :y 0}
                                                  :top-right {:x 6 :y 6}})
      (handle-instructions {:position (execute (first instructions)
                                               position
                                               specs/cardinal-directions)
                            :instructions (rest instructions)})
    :else (str "Invalid position " (adapters/position->cmdline-fmt position))))

(defn handle-explorers [explorers]
  (map #(handle-instructions %) explorers))
