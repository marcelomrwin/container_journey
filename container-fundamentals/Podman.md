## Check installation
```shell
podman run --rm hello-world
```

## Simple container run
```shell
podman run --rm --name hello -p 8180:80 docker/welcome-to-docker
```

## Basic containerfile
```shell
cd basic
podman build -t container-journey/basic .
podman run --rm --name basic -p 8180:80 container-journey/basic
```

## Volume example Linux
```shell
cd volumes
podman run --rm --name volumes -p 8180:80 -v $(pwd)/html:/usr/share/nginx/html:ro nginx:alpine
```

## Volume example MacOS Option 1
```shell
podman machine init --cpus 6 -m 8192 -v <Volume>:<Volume> --now
podman machine stop
podman machine set --rootful
podman machine start
cd volumes
podman run --rm --name volumes -p 8180:80 -v $(pwd)/html:/usr/share/nginx/html:ro nginx:alpine
```

## Volume example MacOS Option 2
```shell
cd volumes
podman volume create NGINX_DATA
podman run --rm --name volumes -p 8180:80 -v NGINX_DATA:/usr/share/nginx/html nginx:alpine
./demo-data.txt volumes:/usr/share/nginx/html
```