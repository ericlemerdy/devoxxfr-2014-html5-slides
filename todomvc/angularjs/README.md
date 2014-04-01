The gruntfile defines two tasks :

-   **`package`**

    Creates a tarball named `app-<VERSION>.tar.gz` (where `VERSION` is the version defined in `package.json`).

-   **`deploy`**

    Push the tarball to the remote server, extract it in `/var/www` and removes it.
    The role of server used to deploy is specified by adding `--config <blue|green>` to the grunt command.

The `default` task calls package then deploy.
