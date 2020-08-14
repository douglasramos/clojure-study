(ns clojure-study.exercism.reverse-string)

(defn my-reverse
  [string]
  (apply str (reduce #(cons %2 %1) [] string)))


(defn my-reverse2
  [string]
  (apply str (into () string)))

(defn my-reverse3
  [string]
  (reduce #(str %2 %1) "" string))

(my-reverse "muito")
