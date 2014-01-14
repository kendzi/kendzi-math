kendzi-straight-skeleton
===========

Implementation of straight skeleton algorithm for polygons with holes. It is based on concept of tracking bisector intersection with queue of events to process and circular list with processed events called lavs. This implementation is highly modified concept described by Petr Felkel and Stepan Obdrzalek [1].  In compare to original this algorithm has new kind of event and support for multiple events which appear in the same distance from edges. It is common when processing degenerate cases caused by polygon with right angles.

Used simple events:
- edge event
- opposite edge split event
- vertex split event

Used complex events for level:
- multi edge event
- pick event
- multi split event



[1] - "Straight Skeleton Implementation" by Petr Felkel and Stepan Obdrzalek in "SCCG 98: Proceedings of the 14th Spring Conference on Computer Graphics," pages 210-218, ISBN 80-223-0837-4

[2] - "Computing Straight Skeletons and Motorcycle Graphs: Theory and Practice" by Stefan Huber June 2011

[3] - "CampSkeleton", by Tom Kelly, http://twak.blogspot.com/2009/05/engineering-weighted-straight-skeleton.html visited 2014

[4] - "Raising Roofs, Crashing Cycles, and Playing Pool: Applications of Data Structure for Finding Pairwise Interactions" by David Eppstein and Jeff Erickson, March 1999

[5] - "Morphing between geometric shapes using straight-skeleton-based interpolation" by Evgeny Yakersberg, May 2004
