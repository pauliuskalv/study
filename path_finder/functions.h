#ifndef FUNKCIJOS_H_INCLUDED
#define FUNKCIJOS_H_INCLUDED
struct city * create ( char ** miestai , int quantity , FILE * duom ) ;
int find_path ( char * from , char * to , char ** cities , int ** values , int quantity ) ;
void delete ( struct int ** values , int quantity ) ;
#endif // FUNKCIJOS_H_INCLUDED
