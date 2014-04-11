file { "/var/git/":
  ensure => directory,
  owner  => "jenkins",
  group  => "nogroup"
}

exec { "/usr/bin/git clone --bare /var/devoxx-2014-mepc /var/git/devoxx-2014-mepc-bare":
  logoutput => "on_failure",
  creates   => "/var/git/devoxx-2014-mepc-bare/config",
  user      => "jenkins",
  require   => File [ "/var/git/" ]
}