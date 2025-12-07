(ns advent-of-code.2025.core
  (:require [clojure.java.io :as io]))

(defn load-file-from-resources
  "Given a filename load file from the
  resources directory in the project"
  [filename]
  (slurp (io/resource filename)))

