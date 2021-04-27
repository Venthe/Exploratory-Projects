# Versioning

## Version 3

Designed to be cross-compatible between Compose and the Docker Engine’s [swarm mode](https://docs.docker.com/engine/swarm/), version 3 removes several options and adds several more.

* Removed: `volume_driver`, `volumes_from`, `cpu_shares`, `cpu_quota`, `cpuset`, `mem_limit`, `memswap_limit`, `extends`, `group_add`. See the [upgrading](https://docs.docker.com/compose/compose-file/compose-versioning/#upgrading) guide for how to migrate away from these. (For more information on `extends`, see [Extending services](https://docs.docker.com/compose/extends/#extending-services).)

* Added: [deploy](https://docs.docker.com/compose/compose-file/compose-file-v3/#deploy)


> **Note**: When specifying the Compose file version to use, make sure to specify both the _major_ and _minor_ numbers. If no minor version is given, `0` is used by default and not the latest minor version. As a result, features added in later versions will not be supported. For example:
>
> ```
> version: "3"
> ```
>
> is equivalent to:
>
> ```
> version: "3.0"
> ```

## Version 3.1

An upgrade of [version 3](https://docs.docker.com/compose/compose-file/compose-versioning/#version-3) that introduces new parameters only available with Docker Engine version **1.13.1+**, and higher.

Introduces the following additional parameters:

* [`secrets`](https://docs.docker.com/compose/compose-file/compose-file-v3/#secrets)

## Version 3.2

An upgrade of [version 3](https://docs.docker.com/compose/compose-file/compose-versioning/#version-3) that introduces new parameters only available with Docker Engine version **17.04.0+**, and higher.

Introduces the following additional parameters:

* [`cache_from`](https://docs.docker.com/compose/compose-file/compose-file-v3/#cache_from) in [build configurations](https://docs.docker.com/compose/compose-file/compose-file-v3/#build)
* Long syntax for [ports](https://docs.docker.com/compose/compose-file/compose-file-v3/#ports) and [volume mounts](https://docs.docker.com/compose/compose-file/compose-file-v3/#volumes)
* [`attachable`](https://docs.docker.com/compose/compose-file/compose-file-v3/#attachable) network driver option
* [deploy `endpoint_mode`](https://docs.docker.com/compose/compose-file/compose-file-v3/#endpoint_mode)
* [deploy placement `preference`](https://docs.docker.com/compose/compose-file/compose-file-v3/#placement)

## Version 3.3

An upgrade of [version 3](https://docs.docker.com/compose/compose-file/compose-versioning/#version-3) that introduces new parameters only available with Docker Engine version **17.06.0+**, and higher.

Introduces the following additional parameters:

* [build `labels`](https://docs.docker.com/compose/compose-file/compose-file-v3/#build)
* [`credential_spec`](https://docs.docker.com/compose/compose-file/compose-file-v3/#credential_spec)
* [`configs`](https://docs.docker.com/compose/compose-file/compose-file-v3/#configs)

## Version 3.4

An upgrade of [version 3](https://docs.docker.com/compose/compose-file/compose-versioning/#version-3) that introduces new parameters. It is only available with Docker Engine version **17.09.0** and higher.

Introduces the following additional parameters:

* [`target`](https://docs.docker.com/compose/compose-file/compose-file-v3/#target) and [`network`](https://docs.docker.com/compose/compose-file/compose-file-v3/#network) in [build configurations](https://docs.docker.com/compose/compose-file/compose-file-v3/#build)
* `start_period` for [`healthchecks`](https://docs.docker.com/compose/compose-file/compose-file-v3/#healthcheck)
* `order` for [update configurations](https://docs.docker.com/compose/compose-file/compose-file-v3/#update_config)
* `name` for [volumes](https://docs.docker.com/compose/compose-file/compose-file-v3/#volume-configuration-reference)

## Version 3.5

An upgrade of [version 3](https://docs.docker.com/compose/compose-file/compose-versioning/#version-3) that introduces new parameters. It is only available with Docker Engine version **17.12.0** and higher.

Introduces the following additional parameters:

* [`isolation`](https://docs.docker.com/compose/compose-file/compose-file-v3/#isolation) in service definitions
* `name` for networks, secrets and configs
* `shm_size` in [build configurations](https://docs.docker.com/compose/compose-file/compose-file-v3/#build)

## Version 3.6

An upgrade of [version 3](https://docs.docker.com/compose/compose-file/compose-versioning/#version-3) that introduces new parameters. It is only available with Docker Engine version **18.02.0** and higher.

Introduces the following additional parameters:

* [`tmpfs` size](https://docs.docker.com/compose/compose-file/compose-file-v3/#long-syntax-3) for `tmpfs`-type mounts

## Version 3.7

An upgrade of [version 3](https://docs.docker.com/compose/compose-file/compose-versioning/#version-3) that introduces new parameters. It is only available with Docker Engine version **18.06.0** and higher.

Introduces the following additional parameters:

* [`init`](https://docs.docker.com/compose/compose-file/compose-file-v3/#init) in service definitions
* [`rollback_config`](https://docs.docker.com/compose/compose-file/compose-file-v3/#rollback_config) in deploy configurations
* Support for extension fields at the root of service, network, volume, secret and config definitions

## Version 3.8

An upgrade of [version 3](https://docs.docker.com/compose/compose-file/compose-versioning/#version-3) that introduces new parameters. It is only available with Docker Engine version **19.03.0** and higher.

Introduces the following additional parameters:

* [`max_replicas_per_node`](https://docs.docker.com/compose/compose-file/compose-file-v3/#max_replicas_per_node) in placement configurations
* `template_driver` option for [config](https://docs.docker.com/compose/compose-file/compose-file-v3/#configs-configuration-reference) and [secret](https://docs.docker.com/compose/compose-file/compose-file-v3/#secrets-configuration-reference) configurations. This option is only supported when deploying swarm services using `docker stack deploy`.
* `driver` and `driver_opts` option for [secret](https://docs.docker.com/compose/compose-file/compose-file-v3/#secrets-configuration-reference) configurations. This option is only supported when deploying swarm services using `docker stack deploy`.
