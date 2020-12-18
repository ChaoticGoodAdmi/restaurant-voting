package com.topjava.kirill.restaurantvoting.service;

import com.topjava.kirill.restaurantvoting.model.Restaurant;
import com.topjava.kirill.restaurantvoting.model.User;
import com.topjava.kirill.restaurantvoting.model.Vote;
import com.topjava.kirill.restaurantvoting.repository.VoteRepository;
import com.topjava.kirill.restaurantvoting.util.exception.NotFoundException;
import com.topjava.kirill.restaurantvoting.util.exception.VoteDeadlineReachedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.topjava.kirill.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
@Slf4j
public class VoteService {

    @Value("#{T(java.time.LocalTime).parse('${vote.deadline}', T(java.time.format.DateTimeFormatter).ofPattern('HH:mm:ss'))}")
    private LocalTime voteDeadlineTime;

    private final VoteRepository voteRepository;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserService userService, RestaurantService restaurantService) {
        this.voteRepository = voteRepository;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public List<Vote> getAll() {
        log.info("Getting all votes");
        return voteRepository.findAll();
    }

    public Vote get(Integer id) {
        log.info("Getting vote {}", id);
        return checkNotFoundWithId(voteRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Vote " + id + " not found")), id);
    }

    public Vote create(LocalDateTime dateTime, Integer userId, Integer restaurantId) {
        LocalTime voteTime = dateTime.toLocalTime();
        if (voteTime.isAfter(voteDeadlineTime)) {
            throw new VoteDeadlineReachedException("You are not allowed to vote after " + voteDeadlineTime);
        }
        User user = userService.get(userId);
        Restaurant restaurant = restaurantService.get(restaurantId);
        Vote vote = new Vote(dateTime.toLocalDate(), user, restaurant);
        log.info("Saving vote: {}", vote);
        return save(vote);
    }

    private Vote save(Vote vote) {
        User user = vote.getUser();
        Optional<Vote> savedVote = voteRepository.findByUserIdAndDate(user.getId(), vote.getDate());
        if (savedVote.isPresent()) {
            return update(savedVote.get(), vote.getRestaurant().getId());
        }
        return voteRepository.save(vote);
    }

    private void delete(int id) {
        checkNotFoundWithId(voteRepository.delete(id) != 0, id);
    }

    private Vote update(Vote vote, int restaurantId) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        vote.setRestaurant(restaurant);
        return voteRepository.save(vote);
    }
}
