(ns clojure-study.for-the-brave.chapter-3.exercises)

;; 1 Use the str , vector , list , hash-map , and hash-set functions.

(str "it's cool" " and nice")
(vector 1 2 3)
(list 1 2 3)
(class (hash-map :a 1 :b 2 :c 3 :c 5))
(hash-set 1 2 3)

;; 2. Write a function that takes a number and adds 100 to it.

;; when using partial, it it not needed to define a new function
;; the partial already return a function
(def add-100
  (partial + 100))

(add-100 3)

;; 3. Write a function, dec-maker , that works exactly like the function inc-maker
;except with subtraction

(def mapset
  (comp set map))

(mapset inc [1 1 2 2])