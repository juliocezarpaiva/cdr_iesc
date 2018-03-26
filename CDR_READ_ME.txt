GitHub repository for version control and organization of the peer register's comparison algorithm of the Linkage Laboratory of the Institute of Collective Health Studies.

The algorithm of peer comparison of Institute of Collective Health Studies Linkage Laboratory is a possible alternative with the purpose of speeding up the manual review of the State of Rio de Janeiro (and counties) health banks data.
To help with the develop email me (jcezarpaiva16@dcc.ufrj.br) with 'cdr_algorithm' as subject.
Thank you, and sorry for my bad english.

*-----------------------------------------------------------------------------------------------------------------------*
The algorithm works on the basis of a master dissertation by Francisca de Fátima de Araújo Lucena.
The Francisca's algorithm was maked to assist in the judgment of links coming from the probabilistic database (probabilistic record linkage). The initial algorithm has been built from the application of twenty different criteria, generating a final score for each link analyzed. The application of the algorithm was developed, originally, in the PHP programming language, but it was reworked by students of the Collective Health Studies Institute, using JAVA programming language.
The actual version of the algorithm applies twenty different criteria (based on the original Francica's algorithm), but all respect the basis of the primary Francisca's thesis.
These criteria are:
criterio[0] = "prim frag igual": checks the equality of the first fragment of the names;
criterio[1] = "ult frag igual": checks the equality of the last fragment of the names;
criterio[2] = "qtd frag iguais": ratio of the fragments equal in the two names sum by the size of the two names sum;
criterio[3] = "qtd frag raros": score maked by the calculus of the ratio between the average frequency of general appearance of the names in the frequency map minus the frequency of the fragments of the names by the average frequency of general appearance, and some stuff more;
criterio[4] = "qtd frag comuns": it works based on the same fundation of the previous criteria, *but is not well applied yet*;
criterio[5] = "qtd frag muito parec": works by comparing the soundex of the fragments of the names;
criterio[6] = "qtd frag abrev": compares the similar abbreviations between the data names.
it would be nice to apply a concept similar to soundex to extract better performance.

criterio[7] = "mae prim frag igual";
criterio[8] = "mae ult frag igual";
criterio[9] = "mae qtd frag iguais";
criterio[10] = "mae qtd frag raros";
criterio[11] = "mae qtd frag comuns";
criterio[12] = "mae qtd frag muito parec";
criterio[13] = "mae qtd frag abrev";

*criterias seven to thirteen works the same way of criteria zero to six.

criterio[14] = "dt iguais": checks the equality of the data on the two databases;
criterio[15] = "dt ap 1digi": check if only one digit of the dates has been changed;
criterio[16] = "dt inv dia": check if only one digit of the dates has been changed
criterio[17] = "dt inv mes": check the inversion of month digits;
criterio[18] = "dt inv ano": check the inversion of year digits;
criterio[19] = "lvnstn apli": check the inversion of day digits;