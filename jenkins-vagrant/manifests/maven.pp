file { "/usr/local/apache-maven/":
  ensure => "directory"
}

exec { "maven3":
  command   => "/bin/tar -zxvf /vagrant/files/apache-maven-3.2.1-bin.tar.gz --directory /usr/local/apache-maven/",
  logoutput => "on_failure",
  user      => "root",
  require   => File [ "/usr/local/apache-maven/" ]
}

file { "/etc/profile.d/maven.sh":
  ensure  => present,
  content => inline_template("
#!/bin/sh

export M2_HOME=/usr/local/apache-maven/apache-maven-3.2.1/
export M2=\$M2_HOME/bin
export PATH=\$M2:\$PATH"),
  owner   => "root",
  group   => "root",
  mode    => 644,
  require => Exec [ "maven3" ]
}