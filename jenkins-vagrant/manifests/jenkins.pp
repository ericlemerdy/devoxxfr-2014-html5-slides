class jenkins {
  include apt

  apt::source { "jenkins":
    location    => "http://pkg.jenkins-ci.org/debian",
    release     => "binary/",
    repos       => "",
    key         => "D50582E6",
    key_source  => "http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key",
    include_src => false
  }

  package { "jenkins":
    ensure  => "installed",
    require => Apt::Source [ "jenkins" ]
  }

  service { "jenkins":
    ensure  => running,
    enable  => true,
    require => Package [ "jenkins" ]
  }
}