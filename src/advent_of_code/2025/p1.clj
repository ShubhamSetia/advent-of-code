(ns advent-of-code.2025.p1
  (:require [advent-of-code.2025.core :as core]
            [clojure.string :as string]))


(def data (core/load-file-from-resources "2025/p1_input.txt"))
(def start-point 50)


(defn parse-data
  "Parse the data from the file - split into lines"
  [data]
  (string/split-lines data))


(defn parse-row
  [row]
  (let [direction (subs row 0 1)
        distance (subs row 1)]
    {:direction direction :distance (Integer/parseInt distance)}))

(defn apply-rotation
  [current-position row]
  (let[{:keys [direction distance]} (parse-row row)]
    (case direction
      "R" (mod (+ current-position distance) 100)
      "L" (mod (- current-position distance) 100)
      current-position)))
  
(defn solve-part-1
  [rotations]
  (->> (reductions apply-rotation start-point rotations)
      (filter zero?)
      (count)))


(defn -main
   [& _args]
  (let [rotations (parse-data data)] 
    (println "Count of 0s:" (solve-part-1 rotations))))