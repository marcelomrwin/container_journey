# Journey to Containers

## Preparation

### Install Podman Desktop

https://podman-desktop.io/

### Mac Users
```shell
podman machine init --cpus 6 -m 8192 -v <VOLUME_BASE>:<VOLUME_BASE> --now
podman machine stop
podman machine set --rootful
podman machine start
```

### Install Ansible

https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html

## Configure the playbook

In the playbook.yaml file, you can change the value of the `user_language` variable to:
    * en = English texts
    * es = Spanish texts

```yaml
vars:
    user_language: "es"
```

## Run the workshop

```shell
ansible-playbook playbook.yaml
```

## Extra Commands

### Show labels 
```shell
podman inspect --format '{{ json .Config.Labels }}' <CONTAINER_NAME> | jq
```

### Filter labels
```shell
podman inspect --format '{{ index .Config.Labels "org.opencontainers.image.description" }}' <CONTAINER_NAME>
```

## References
* https://www.redhat.com/en/blog/run-containers-mac-podman
* https://developers.redhat.com/topics/containers