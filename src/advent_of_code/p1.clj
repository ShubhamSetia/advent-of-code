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
  "Computes the fuel required given the mass"
  [mass]
  (-> mass
      (quot 3)
      (- 2)
      (max 0)))


(defn total-fuel
  "Given a sequence of masses compute the total
  fuel required"
  [mass-seq]
  (reduce + (map compute-fuel mass-seq)))


(defn -main
  [& args]
  (let [mass-seq (parse-data)]
    (total-fuel mass-seq)))
