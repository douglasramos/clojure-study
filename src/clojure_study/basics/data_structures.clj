(ns clojure-study.basics.data-structures)

;; All About collections

;; "Collection", "Sequential" and "Seq" are abstractions.
;; These abstractions are usually related to an interface that a concrete type builds itself upon

;; Collection is the greater abstraction that contains Sequential, Associative and Counted.

;;
;; Collection
;; ├── Sequential    # Implements Sequential Interface. is a marker interface (no methods) and is
;;                   a promise that the collection can be iterated over in a defined order
;;     ├── Seq       # Implements ISeq (first, rest, cons)
;; ├── Associative   # implements a associativity of some kind. By indexes or keys
;; ├── Counted       # Something that can count (basically all collection (but with different cost, except LazySeq)


;; Since the seq function can take a collection, ultimately any collection can be tranformed into a
;; datatype that can perform like a seq.
;; Example: [1 2 3] => clojure.lang.PersistentVector
;; (seq [1 2 3]) => clojure.lang.PersistentVector$ChunkedSeq

(seq? [1 2 3])                                              ;; false
(seq? (seq [1 2 3]))                                        ;; true

;; List (Collection -> Sequential -> Seq) + Counted
'(1 2 3)
(list 1 2 3)
((juxt coll? sequential? seq?) '(1 2 3))                     ; [true, true, true]

;; Vector (Collection -> Associative) + Counted + Sequential
[1 2 3]
(vector 1 2 3)
((juxt coll? sequential? seq?) [1 2 3])                    ;; [true, true, false]

;; Map (Collection -> Associative) + Counted
{:bar 1 :foo 2 :lee 3}
(zipmap [:year :month :day] (clojure.string/split "2013-02-18" #"-"))
((juxt coll? sequential? seq?) {:bar 1 :foo 2 :lee 3})      ;; [true, false, false]
(associative? {:bar 1 :foo 2 :lee 3})

; a sequence of map entries
(seq {:bar 1 :foo 2 :lee 3})                                ;; ([:bar 1] [:foo 2] [:lee 3])

;; Array maps and hash maps have the same interface, but array maps have O(N)
;; lookup complexity (i.e. it is implemented as an simple array of entries), while
;; hash maps have O(1) lookup complexity.
;; Array maps have the advantage (which you don't need most of the time) that
;; they maintain insertion order, so when you perform any operation that iterates
;; over the map (say, map or reduce), you can process the entries in the same order
;; as when you inserted them.
;; Note that if you "modify" (in the persistent-collection sense) an array map
;; repeatedly, at some point it will become a hash map. e.g.

;; Set (Collection -> Counted
#{1 2 3}                                                    ;; #{1 2 3}
(set '(1 2 2 3))
((juxt coll? sequential? seq?) #{1 2 3})                    ;; [true, false, false]
(counted? #{1 2 3})


;;      A word on Seqs

;; 1. getting the length of a seq can be costy:

(let [s (range 1e6)]
  (time (count s)))

(let [s (apply list (range 1e6))]
  (time (count s)))

;; 2. seqs can be lazy (like IEnumerable c#) that means
;; it resolves the next item by demand. It doesn't know
;; all the items till it is requested

;; 3. seqs can be infinite, thus uncountable.
;; That is possible by the LazySeq. Since it defers the load of
;; an item till its actual use, it can have a undefined (thus, infinite)
;; number of elements.

;;       Conj and Cons

;; add single entry to the collection
(conj [1 2 3] 4)                                            ;; returns a collection [1 2 3 4]

;; add multiple entry to the collection
(conj [1 2 3] 4 10 15)

;; add single entry (vector) to the collection
(conj [1 2 3] [4 10 15])

;; add to the head (not tail) becaus conj add to the
;; optimum position in the collection. On list case
;; it is the head
(conj '(1 2 3) 4 2)                                         ;; (2 4 1 2 3)

;; add online one single entry to the seq always on the head
(cons 4 [1 2 3])                                            ;;  return a seq (4 1 2 3)

;;        Vector vs List

;; to retrieve a specific value on a Vector use Get
;; to retrieve a specific value on a List use nth.
;; This operation is much slower for a List than a Vector
;; This is because
;; Clojure has to traverse all n elements of a list to get to the nth, whereas it
;; only takes a few hops at most to access a vector element by its index.

;; Tip

;; you can use '() or ()
(conj () 3 2 1)                                             ;; (1 2 3)
(conj '() 3 2 1)                                            ;; (1 2 3)

;; But with not empty, the quote is mandatory. This prevents
;; from being evalueted, that is being considered as a form istead
;; o a list
(class '(1 2 3))

;; This exable will throw an error cause it is not possible
;; to cas a number (1) to IFn (function). First position of a form needs to be
;; a function
;;(class (1 2 3))


;;

;; Currying is a specifc case of partial when you break down a
;; a function that takes multiples arguments into a series
;; a functions that each take only one argument
;; Partial is more generic, allowing these return
;; functions have one or multiple arguments. On partial
;; the important thing is that the funcion is evalueted partially
;; no matter exactly in what amount.

(defn my-sum [x y]
  (+ x y))

(defn my-sum-curried [x]
  #(+ % x))

(defn my-sum-partial [x]
  (partial + x))

((my-sum-curried 3) 2)

((my-sum-partial 3) 2)

(defn my-sum-3 [x]
  (partial + x ))

;; there's no automatic currying on clojure that is why we implemented
;; explicit return a anonimous function or a partial. We need to be
;; explicit. More on this at: https://dragan.rocks/articles/18/Fluokitten-080-Fast-function-currying-in-Clojure

