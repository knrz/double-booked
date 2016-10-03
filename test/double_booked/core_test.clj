(ns double-booked.core-test
  (:require [clojure.test :refer :all]
            [clj-time.core :as t]
            [double-booked.test-helper :as helper]
            [double-booked.core :as d]))

(def events (helper/stubbed-events 16))

(deftest events-pair
  (testing "A pair of events is sorted by start time"
    (let [[event-a event-b] (take 2 (shuffle events))
          actual (d/pair event-a event-b)]
      (if (t/before? (d/start-time event-a) (d/start-time event-b))
        (is (= [event-a event-b] actual))
        (is (= [event-b event-a] actual))))))

(deftest empty-events-seq
  (testing "An empty events sequence should return an empty list"
    (let [expected []
          actual (d/pairs [])]
      (is (= expected actual)))))

;; (deftest two-events-seq
;;   (testing "A seq of two overlapping or non-overlapping events should behave properly"
;;     (let [event-a (rand-nth events)
;;           event-b (rand-nth events)
;;           expected (if (t/overlaps? (:time event-a) (:time event-b))
;;                      [(d/pair event-a event-b)]
;;                      [])]
;;       (is (= expected (d/pairs [event-a event-b]))))))

