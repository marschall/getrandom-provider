#define _GNU_SOURCE

#include <stdio.h>
#include <sys/random.h>
#include <sys/errno.h>

int main(void)
{
    printf("EFAULT: %d\n", EFAULT);
    printf("EINTR: %d\n", EINTR);
    printf("EINVAL: %d\n", EINVAL);

    return 0;
}
