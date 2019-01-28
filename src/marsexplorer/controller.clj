(ns marsexplorer.controller
  (:require [marsexplorer.logic :as logic]
            [marsexplorer.adapters :as adapters]))

(defn- execute [instruction position cardinal-directions]
  (case instruction
    :M (logic/move position)
    (logic/turn instruction position cardinal-directions)))

(defn- handle-instructions
  [cardinal-directions mars-length {:keys [position instructions]}]
  (cond
    (seq instructions)
      (adapters/position->cmdline-fmt position)
    (logic/validPosition? position mars-length)
      (handle-instructions cardinal-directions
                           mars-length
                           {:position (execute (first instructions)
                                               position
                                               cardinal-directions)
                            :instructions (rest instructions)})
    :else (str "Invalid position " (adapters/position->cmdline-fmt position))))

(defn handle-settings
  [cardinal-directions {:keys [mars-length explorers]}]
  (map #(handle-instructions cardinal-directions
                             mars-length
                             %)
       explorers))
