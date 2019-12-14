(ns advent-of-code.p1
  "Advent of code problem1
  https://adventofcode.com/2019/day/1"
  (:require [advent-of-code.core :as core]
            [clojure.string :as string]))


(def data (core/load-file-from-resources "data1.txt"))


(defn parse-data
  "Split the data by lines and
  convert them into integers"
  []
  (map #(Integer/parseInt %)
       (string/split-lines data)))


(defn compute-fuel
  "Computes the fuel-value of the given mass
  If value is less than 0 return 0"
  [mass]
  (-> mass
      (quot 3)
      (- 2)
      (max 0)))


(defn total-fuel-by-1-elf
  "Compute the total fuel required for a mass"
  [mass]
  (reduce + (rest (loop [m [mass]]
                    (if (> (last m) 0)
                      (recur (conj m
                                   (compute-fuel (last m))))
                      m)))))


(defn part-1
  "Add the fuel value of each mass"
  [mass-seq]
  (reduce + (map compute-fuel mass-seq)))


(defn part-2
  "Compute the total-fuel value for each mass and add them."
  [mass-seq]
  (reduce + (map total-fuel-by-1-elf mass-seq)))


(defn -main
  [& args]
  (let [mass-seq (parse-data)]
    (println (part-1 mass-seq)
             (part-2 mass-seq))))
