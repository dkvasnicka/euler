(require "alexandria")
(require "iterate")
(defpackage :euler
  (:use :cl :iterate :alexandria)
  (:shadowing-import-from :iterate while until))
