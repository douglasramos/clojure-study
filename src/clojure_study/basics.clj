(ns clojure-study.basics)

; or and and
(or false true)                                             ; true

(or false false nil)                                        ; nil

(or false nil "legal" true nil 2)                           ; "legal"

(or 0)                                                      ; 0 (true)

(and false 23 true)                                         ; false

(and true true 0)                                           ; 0


; fib

(defn fib
  "fib recursion"
  [n]
  (if (or (= n 0) (= n 1))
    n
    (+ (fib (- n 1)) (fib (- n 2)))))


(fib 10)                                                    ; 55

(defn fib-tail
  "fib tail recursion"
  [n]
  (loop [a 0 b 1 count n]
    (if (= count 1)
      b
      (recur b
             (+ a b)
             (dec count)))))

(fib-tail 10)

; fatorial

(defn factorial
  "factorial recursion"
  [n]
  (if (= n 2)
    n
    (* n (factorial (dec n)))))

(factorial 4)

(defn factorial-tail
  "Factorial tail recursion"
  [n]
  (loop [count n res 1]
    (if (= count 1)
      res
      (recur (dec count) (* count res)))))                  ; not use initial valu n here

(factorial-tail 4)

;; maps
; this going to return a literal seq no matter data structure
; serves as an input. It can be a list, vector or even a map

; apply a function to each item of the seq
(map inc [1 2 3])

; apply a str function using an element of each collection
; as input for that given function
(map str ["a" "b" "c"] ["A" "B" "C"])

(def human-consuption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human   human
   :critter critter})

(map unify-diet-data human-consuption critter-consumption)

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))

; this snippet create as anonimous function
; that receives another funtion (from the collection that
; will be iterated by the map and apply that function
; to the entry numbers collection
(defn stats
  [numbers]
  (map #(% numbers)) [sum count avg])

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

; we can also pass a keyword as the function for the map
(map :real identities)

;; Reduce

; the important thing here is that our map entry ir readed by the reduce
; as ([:max 30] [:min 10]) -> [key val]
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})

