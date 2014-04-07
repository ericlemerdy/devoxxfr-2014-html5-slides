class jenkins {
  include apt

  apt::key { "jenkins":
    key        => "D50582E6",
    key_source => "http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key"
  }

  file { "/etc/apt/sources.list.d/jenkins.list":
    ensure   => "file",
    content  => "deb http://pkg.jenkins-ci.org/debian binary/",
    require  => Apt::Key[ "jenkins" ]
  }

  package { "jenkins":
    ensure  => "installed",
    require => File [ "/etc/apt/sources.list.d/jenkins.list" ]
  }

  service { "jenkins":
    ensure  => running,
    enable  => true,
    require => Package [ "jenkins" ]
  }
}