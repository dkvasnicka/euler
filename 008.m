(* ::Package:: *)

input = Map[ToExpression, 
	StringSplit[StringReplace[
		Import["008.txt"], {WhitespaceCharacter -> ""}], ""]];

fives = Map[Take[input, {#, # + 12}] &, Range[987]];
Print[Max[Map[Product[i, {i, #}] &, fives]]]
