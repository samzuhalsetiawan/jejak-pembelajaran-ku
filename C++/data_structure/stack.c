#include <stdio.h>
#define maxsize 100

typedef struct
{
    int top;
    char items[maxsize];
} stack;

void inisialize(stack *s)
{
    s->top = -1;
}
void push(stack *s, char x)
{
    if (s->top >= (maxsize - 1))
        printf("\nERROR: Stack is Full!!");
    else
    {
        s->top = s->top + 1;
        s->items[s->top] = x;
        printf("\nPush Succeed");
    }
}
void pop(stack *s, char *x)
{
    if (s->items < 0)
        printf("\nERROR: the stack is empty!!");
    else
    {
        *x = (s->items[s->top]);
        s->top = s->top - 1;
        printf("\nPop Succeed");
    }
}
void show(stack *s)
{
    printf("\nIsi Stack :\n");
    for (int i = s->top; i >= 0; i--)
        printf("\t%c\n", s->items[i]);
    printf("\n");
}
int Is_Empty(stack *s)
{
    if (s->top <= -1)
        return (1);
    else
    {
        return (0);
    }
}
int Is_Full(stack *s)
{
    if (s->top == maxsize - 1)
        return (1);
    else
    {
        return (0);
    }
}

void main()
{
    stack *my_stack, s;
    char item, *x;
    my_stack = &s;
    x = &item;
    inisialize(my_stack);

    push(my_stack, 'A');
    push(my_stack, 'R');
    push(my_stack, 'I');
    push(my_stack, 'F');
    show(my_stack);
    pop(my_stack, x);
    pop(my_stack, x);
    show(my_stack);
    pop(my_stack, x);
    pop(my_stack, x);
    show(my_stack);
}