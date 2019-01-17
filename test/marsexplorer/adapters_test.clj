(ns marsexplorer.adapters-test
  (:require [midje.sweet :refer :all]
            [marsexplorer.adapters :as adapters]))

(fact "Convert settings into a list of explorer configurations"
  (fact "when settings is empty"
    (adapters/settings->explorers-config []) => [])

  (fact "when settings have one explorer configuration"
    (adapters/settings->explorers-config ["5 5"
                                          "1 2 N"
                                          "LMLMLMLMM"]) => '({:instructions [:L :M :L :M :L :M :L :M :M]
                                                              :position {:direction :N
                                                                         :x 1
                                                                         :y 2}}))

  (fact "when seetings has more than one explorer configuration"
    (adapters/settings->explorers-config ["5 5"
                                          "1 2 N"
                                          "LMLMLMLMM"
                                          "3 3 E"
                                          "MMRMMRMRRM"]) => '({:instructions [:L :M :L :M :L :M :L :M :M]
                                                               :position {:direction :N
                                                                          :x 1
                                                                          :y 2}}

                                                               {:instructions [:M :M :R :M :M :R :M :R :R :M]
                                                               :position {:direction :E
                                                                          :x 3
                                                                          :y 3}})))
