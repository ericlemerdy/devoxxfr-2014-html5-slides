class jenkins {
  include apt

  exec { 'apt_update_once':
    command     => "/usr/bin/apt-get update",
    logoutput   => "on_failure",
    user        => "root"
  }

  package { "daemon"              : ensure => "installed", require => Exec [ "apt_update_once" ] }
  package { "adduser"             : ensure => "installed", require => Exec [ "apt_update_once" ] }
  package { "psmisc"              : ensure => "installed", require => Exec [ "apt_update_once" ] }
  package { "default-jre-headless": ensure => "installed", require => Exec [ "apt_update_once" ] }

  exec { "install_jenkins":
    command => "/usr/bin/dpkg -i /vagrant/files/jenkins_1.558_all.deb",
    creates => "/var/lib/jenkins/",
    user    => "root",
    require => [
      Package [ "daemon", "adduser", "psmisc", "default-jre-headless" ],
      Exec [ "apt_update_once" ]
    ]
  }

  service { "jenkins":
    ensure  => running,
    enable  => true,
    require => Exec [ "install_jenkins" ]
  }

  user { "jenkins":
    groups  => "vagrant",
    require => Exec [ "install_jenkins" ]
  }
}