package com.bumble.appyx.routingsourcedemos.promoter.routingsource

import com.bumble.appyx.routingsourcedemos.promoter.routingsource.Promoter.TransitionState
import com.bumble.appyx.v2.core.routing.RoutingElement
import com.bumble.appyx.v2.core.routing.RoutingElements

typealias PromoterElement<T> = RoutingElement<T, TransitionState>

typealias PromoterElements<T> = RoutingElements<T, TransitionState>
