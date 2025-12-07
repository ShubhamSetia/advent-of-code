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
  "Apply a rotation to the current position and return new position"
  [current-position row]
  (let [{:keys [direction distance]} (parse-row row)]
    (case direction
      "R" (mod (+ current-position distance) 100)
      "L" (mod (- current-position distance) 100)
      current-position)))

(defn solve-part-1
  "Part 1: Count zeros at the end of each rotation.
   Uses reductions to track all positions, then counts how many are zero."
  [rotations]
  (->> (reductions apply-rotation start-point rotations)
       (filter zero?)
       (count)))

(defn count-zeros-during-rotation
  "Count zeros during a single rotation (excluding start and end positions).
   Returns the count of zeros that occur during the rotation."
  [start-pos rotation]
  (let [{:keys [direction distance]} (parse-row rotation)
        ;; Step 1: For large distances, count full cycles
        full-cycles (quot distance 100)
        distance' (mod distance 100)
        ;; Step 2: Count zeros during wrapping (process remainder)
        wrap-zeros (case direction
                    "L" (let [next-dial (- start-pos distance')]
                          (if (and (< next-dial 0) (> start-pos 0)) 1 0))
                    "R" (let [next-dial (+ start-pos distance')]
                          (if (and (>= next-dial 100) (not= next-dial 100)) 1 0))
                    0)]
    (+ full-cycles wrap-zeros)))

(defn solve-part-2
  "Part 2: Count zeros at the end AND during rotations.
   Uses reductions to track positions, then maps over rotations to count zeros during each.
   Reuses solve-part-1 for zeros at end."
  [rotations]
  (let [positions (reductions apply-rotation start-point rotations)
        ;; Count zeros during each rotation using map and reduce
        zeros-during (->> (map count-zeros-during-rotation positions rotations)
                          (reduce +))]
    (+ zeros-during (solve-part-1 rotations))))


(defn -main
   [& _args]
  (let [sample-rotations ["L68" "L30" "R48" "L5" "R60" "L55" "L1" "L99" "R14" "L82"]
        rotations (parse-data data)]
    (println "=== Testing Sample Input ===")
    (println "Part 1:" (solve-part-1 sample-rotations))
    (println "Part 2:" (solve-part-2 sample-rotations))
    (println "Expected Part 1: 3")
    (println "Expected Part 2: 6")
    (println "\n=== Solving Actual Input ===")
    (println "Answer part 1:" (solve-part-1 rotations))
    (println "Answer part 2:" (solve-part-2 rotations))))