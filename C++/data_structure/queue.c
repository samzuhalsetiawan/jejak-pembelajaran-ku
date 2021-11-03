#include <stdio.h>
#define maxsize 100

typedef struct
{
    int jumlah;
    int depan;
    int belakang;
    char items[maxsize];
} queue;

void initialize(queue *q)
{
    q->jumlah = 0;
    q->depan = 0;
    q->belakang = 0;
}
void hapus(queue *q, char *x)
{
    if (q->jumlah == 0)
    {
        printf("\nError: queue s");
    }
    else
    {
        *x = q->items[q->depan];
        q->depan = (q->depan + 1);
        --(q->jumlah);
    }
}
void insert(queue *q, char x)
{
    if (q->jumlah == maxsize)
    {
        printf("\nError: queue sudah penuh");
    }
    else
    {
        q->items[q->belakang] = x;
        q->belakang = (q->belakang + 1);
        ++(q->jumlah);
    }
}
void show(queue *q)
{
    printf("\nIsi Queue:\n");
    for (int i = q->depan; i < q->belakang; i++)
    {
        printf("%c ", q->items[i]);
    }
    printf("\n");
}
int Is_Empty(queue *q)
{
    if (q->jumlah == 0)
    {
        return (1);
    }
    else
    {
        return (0);
    }
}
int Is_Full(queue *q)
{
    if (q->jumlah == maxsize)
    {
        return (1);
    }
    else
    {
        return (0);
    }
}

void main()
{
    queue *my_queue, q;
    char item, *x;
    my_queue = &q;
    x = &item;
    initialize(my_queue);
    insert(my_queue, 'A');
    insert(my_queue, 'R');
    insert(my_queue, 'I');
    insert(my_queue, 'F');
    show(my_queue);
    hapus(my_queue, x);
    hapus(my_queue, x);
    show(my_queue);
    hapus(my_queue, x);
    hapus(my_queue, x);
    show(my_queue);
}