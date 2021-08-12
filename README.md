# http_pages


## conf.d

```conf
    error_page 400 /error_pages/400.html;
    error_page 401 /error_pages/401.html;
    error_page 402 /error_pages/402.html;
    error_page 403 /error_pages/403.html;
    error_page 404 /error_pages/404.html;
    error_page 405 /error_pages/405.html;
    error_page 406 /error_pages/406.html;
    error_page 407 /error_pages/407.html;
    error_page 408 /error_pages/408.html;
    error_page 409 /error_pages/409.html;
    error_page 410 /error_pages/410.html;
    error_page 411 /error_pages/411.html;
    error_page 412 /error_pages/412.html;
    error_page 413 /error_pages/413.html;
    error_page 414 /error_pages/414.html;
    error_page 415 /error_pages/415.html;
    error_page 416 /error_pages/416.html;
    error_page 417 /error_pages/417.html;
    error_page 418 /error_pages/418.html;
    error_page 422 /error_pages/422.html;
    error_page 423 /error_pages/423.html;
    error_page 424 /error_pages/424.html;
    error_page 425 /error_pages/425.html;
    error_page 426 /error_pages/426.html;
    error_page 428 /error_pages/428.html;
    error_page 429 /error_pages/429.html;
    error_page 431 /error_pages/431.html;
    error_page 451 /error_pages/451.html;
    error_page 500 /error_pages/500.html;
    error_page 501 /error_pages/501.html;
    error_page 502 /error_pages/502.html;
    error_page 503 /error_pages/503.html;
    error_page 504 /error_pages/504.html;
    error_page 505 /error_pages/505.html;
    error_page 506 /error_pages/506.html;
    error_page 507 /error_pages/507.html;
    error_page 508 /error_pages/508.html;
    error_page 509 /error_pages/509.html;
    error_page 510 /error_pages/510.html;
    error_page 511 /error_pages/511.html;
    location ^~ /error_pages {
        internal;
        root   /data/nginx/html/error_pages/;
    }
```