(ns clojure-study.exercism.hello-world)

; hellow world with arity
; and airty definition can call anothe arity
; definition. This is useful to create default parameters

(defn hello
  ([] (hello "World"))
  ([name] (str "Hello, " name "!")))
