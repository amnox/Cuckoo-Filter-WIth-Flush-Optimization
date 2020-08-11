# Optimized Cuckoo Filters for Efficient Distributed SDN and NFV Applications

Membership testing has many networking applications like distributed caching, peer to peer networks, or resource routing, to name a few. Several studies have reported the advantages of using membership testing in Software Defined Networking, and Bloom Filters have been widely adopted for that purpose. Cuckoo Filters is a recently proposed alternative to Bloom that outperforms them in terms of speed and memory efficiency, with some drawbacks. In this paper, we propose an Optimized Cuckoo Filter (OCF) design that limits some of the Cuckoo Filter drawbacks and gives a better-amortized search time, with less false positives. We then present an implementation of Optimized Cuckoo Filter in distributed SDN and NFV applications, with customizable parameters that enable the data structure to adapt to different workloads. We discuss the use cases of this data structure in SDN and show the performance gain when using our solution with proper configuration. We also show the benefits of this data structure in different SDN and NFV applications by simulating real-world scenarios: content-centric caching and Virtual Firewall as a Network Function and invoke dialog for the widespread adoption of this data structure outside academia through open-source collaboration.

## Java Setup Instructions
- Clone repository
- Build ``gradle build``
- Run ``gradle run``

