(ns marsexplorer.adapters-test
  (:require [midje.sweet :refer :all]
            [marsexplorer.adapters :as adapters]
            [marsexplorer.config :as config]))

(fact "Parse a file content into a settings map"
  (fact "when file content contains more than 1 explorers config"
    (adapters/file-content->settings! (str "5 5\n"
                                           "1 2 N\n"
                                           "LMLM\n"
                                           "3 3 E\n"
                                           "MMRM")) =>
                                      {:explorers [{:instructions [:L
                                                                   :M
                                                                   :L
                                                                   :M]
                                                    :position {:direction :N
                                                               :x 1
                                                               :y 2}}
                                                   {:instructions [:M
                                                                   :M
                                                                   :R
                                                                   :M]
                                                    :position {:direction :E
                                                               :x 3
                                                               :y 3}}]
                                       :mars-length {:bottom-left {:x 0 :y 0}
                                                     :top-right   {:x 5 :y 5}}})

  (fact "when file content contains only 1 explorer config"
    (adapters/file-content->settings! (str "5 5\n"
                                   		     "1 2 N\n"
                                   		     "LMLM\n")) =>
                                      {:explorers [{:instructions [:L
                                                                   :M
                                                                   :L
                                                                   :M]
                                                    :position {:direction :N
                                                               :x 1
                                                               :y 2}}]
                                       :mars-length {:bottom-left {:x 0 :y 0}
                                                     :top-right   {:x 5 :y 5}}})

  (fact "when explorers config are incompleted, should not create it"
    (adapters/file-content->settings! (str "5 5\n"
                                           "LMLM\n")) =>
                                     {:explorers ()
                                      :mars-length {:bottom-left {:x 0 :y 0}
                                                    :top-right {:x 5 :y 5}}})

  (fact "when file content contains invalid data, should throw an exeption"
    (adapters/file-content->settings! "") => (throws Exception)

    (adapters/file-content->settings! (str "invalid\n"
                                           "1 2 N\n"
                                           "LRM\n")) => (throws Exception)

    (adapters/file-content->settings! (str "5 5\n"
                                     	     "invalid\n"
                                     	     "LRM\n")) => (throws Exception)

    (adapters/file-content->settings! (str "5 5\n"
                                     	     "1 2 N\n"
                                     	     "invalid\n")) => (throws Exception)))

(fact "Parse a position into a command line formart"
	(adapters/position->cmdline-fmt {:x 2 :y 4 :direction :E}) => "2 4 E")