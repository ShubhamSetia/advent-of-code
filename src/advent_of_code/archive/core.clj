(ns advent-of-code.archive.core
  (:require [clojure.java.io :as io]))

(defn load-file-from-resources
  "Given a filename load file from the
  resources directory in the projecet"
  [filename]
  (slurp (io/resource filename)))
