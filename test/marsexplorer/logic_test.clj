(ns marsexplorer.logic_test
  (:require [midje.sweet :refer :all]
            [marsexplorer.specs :as specs]
            [marsexplorer.logic :as logic]))

(fact "Turn explorer face direction"
	(let [position {:x 3 :y 4}]
		(fact "to the left"
			(fact "when facing north"
					(logic/turn :L
											specs/cardinal-directions
											(assoc position :direction :N)) => (assoc position :direction :W))
			(fact "when facing east"
				(logic/turn :L
										specs/cardinal-directions
										(assoc position :direction :E)) => (assoc position :direction :N))
			(fact "when facing south"
				(logic/turn :L
										specs/cardinal-directions
										(assoc position :direction :S)) => (assoc position :direction :E))
			(fact "when facing west"
				(logic/turn :L
										specs/cardinal-directions
										(assoc position :direction :W)) => (assoc position :direction :S)))
		(fact "to the right"
			(fact "when facing north"
				(logic/turn :R
									  specs/cardinal-directions
									  (assoc position :direction :N)) => (assoc position :direction :E))
			(fact "when facing east"
				(logic/turn :R
									  specs/cardinal-directions
									  (assoc position :direction :E)) => (assoc position :direction :S))
			(fact "when facing south"
				(logic/turn :R
									  specs/cardinal-directions
									  (assoc position :direction :S)) => (assoc position :direction :W))
			(fact "when facing west"
				(logic/turn :R
										specs/cardinal-directions
										(assoc position :direction :W)) => (assoc position :direction :N)))))

(fact "move"
	(let [position {:x 1 :y 1}]
		(fact "when facing north"
				(logic/move (assoc position :direction :N)) => {:direction :N :x 1 :y 2})
		(fact "when facing east"
				(logic/move (assoc position :direction :E)) => {:direction :E :x 2 :y 1})
		(fact "when facing south"
				(logic/move (assoc position :direction :S)) => {:direction :S :x 1 :y 0})
		(fact "when facing west"
				(logic/move (assoc position :direction :W)) => {:direction :W :x 0 :y 1})))
