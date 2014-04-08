class nodejs {
  include apt

  package { "python-software-properties":
    ensure  => installed,
    require => Exec [ "apt-get update" ]
  }

  exec { "apt-get update after ppa":
    command => "/usr/bin/apt-get update",
    user    => root
  }

  exec { "add nodejs ppa":
    command => "/usr/bin/apt-add-repository ppa:chris-lea/node.js",
    creates => "/etc/apt/sources.list.d/chris-lea-node_js-precise.list",
    require => Package [ "python-software-properties"],
    notify  => Exec [ "apt-get update after ppa" ]
  }

  package { "nodejs":
    ensure  => "installed",
    require => Exec [ "apt-get update after ppa" ]
  }
}