package com.baeldung.dropwizard.introduction.resource;

import com.baeldung.dropwizard.introduction.domain.Brand;
import com.baeldung.dropwizard.introduction.repository.BrandRepository;
import org.glassfish.jersey.server.ManagedAsync;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
public class BrandResource {
    private final int defaultSize;
    private final BrandRepository brandRepository;

    public BrandResource(final int defaultSize, final BrandRepository brandRepository) {
        this.defaultSize = defaultSize;
        this.brandRepository = brandRepository;

    }


/*
    @GET
    public List<Brand> getBrands(

            @QueryParam("size") final Optional<Integer> size
    ) throws InterruptedException {



            TimeUnit.SECONDS.sleep(10);
        return brandRepository.findAll(size.orElse(defaultSize));
    }*/


    @GET
    @ManagedAsync
    public void getBrands(
            @Suspended final AsyncResponse asyncResponse,
            @QueryParam("size") final Optional<Integer> size
    ) throws InterruptedException {

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        asyncResponse.resume(brandRepository.findAll(size.orElse(defaultSize)));

    }

    @GET
    @Path("/{id}")
    public Brand getById(@PathParam("id") final Long id) {
        return brandRepository
          .findById(id)
          .orElseThrow(RuntimeException::new);
    }
}
