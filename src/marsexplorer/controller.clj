(ns marsexplorer.controller
  (:require [marsexplorer.logic :as logic]
            [marsexplorer.adapters :as adapters]))

(defn- execute [instruction position]
  (case instruction
    :M (logic/move position)
    (logic/turn instruction position)))

(defn- handle-instructions
  [mars-length {:keys [position instructions]}]
  (cond
    (empty? instructions)
      (adapters/position->cmdline-fmt position)
    (logic/validPosition? position mars-length)
      (handle-instructions mars-length
                           {:position (execute (first instructions)
                                               position)
                            :instructions (rest instructions)})
    :else
      (str "Invalid position " (adapters/position->cmdline-fmt position))))

(defn handle-settings [{:keys [mars-length explorers]}]
  (map #(handle-instructions mars-length %) explorers))
